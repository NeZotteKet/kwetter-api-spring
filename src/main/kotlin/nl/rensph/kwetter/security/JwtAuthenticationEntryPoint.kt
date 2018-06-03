package nl.rensph.kwetter.security

import org.slf4j.LoggerFactory
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse?,
                          e: org.springframework.security.core.AuthenticationException) {
        logger.error("Responding with unauthorized error. Message - {}", e.message)
        httpServletResponse!!.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                        "Sorry, You're not authorized to access this resource.")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)
    }
}