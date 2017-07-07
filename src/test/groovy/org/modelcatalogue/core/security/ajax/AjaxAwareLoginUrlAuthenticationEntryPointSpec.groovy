package org.modelcatalogue.core.security.ajax

import org.springframework.security.authentication.BadCredentialsException
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AjaxAwareLoginUrlAuthenticationEntryPointSpec extends Specification {

    def "NOT_AUTHORIZED is returned for AJAX calls"() {
        HttpServletRequest request = Mock(HttpServletRequest)
        HttpServletResponse response = Mock(HttpServletResponse)

        AjaxAwareLoginUrlAuthenticationEntryPoint entryPoint = new AjaxAwareLoginUrlAuthenticationEntryPoint("/api/login")
        entryPoint.statusForAjaxCalls = status

        when:
        entryPoint.commence(request, response, new BadCredentialsException('Blah'))

        then:
        entryPoint.statusForAjaxCalls == status
        1 * request.getHeader('Accept') >> 'application/json'
        1 * response.sendError(status, _)

        where:
        status << [HttpServletResponse.SC_UNAUTHORIZED, HttpServletResponse.SC_FORBIDDEN]

    }

    def "NOT_AUTHORIZED is not returned for other calls"() {
        HttpServletRequest request = Mock(HttpServletRequest)
        HttpServletResponse response = Mock(HttpServletResponse)

        AjaxAwareLoginUrlAuthenticationEntryPoint entryPoint = new AjaxAwareLoginUrlAuthenticationEntryPoint("/api/login")

        when:
        entryPoint.commence(request, response, new BadCredentialsException('Blah'))

        then:
        thrown(NullPointerException) // calling super causes this
        1 * request.getHeader('Accept') >> 'text/plain'
        0 * response.sendError(HttpServletResponse.SC_UNAUTHORIZED, _)

    }

}