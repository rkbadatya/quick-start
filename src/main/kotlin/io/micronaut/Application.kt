package io.micronaut

import io.micronaut.runtime.Micronaut.*
import io.dekorate.kubernetes.annotation.KubernetesApplication
import io.dekorate.kubernetes.annotation.Label
import io.dekorate.kubernetes.annotation.Port
import io.dekorate.kubernetes.annotation.Probe
import io.dekorate.prometheus.annotation.EnableServiceMonitor
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*

@OpenAPIDefinition(
    info = Info(
            title = "quick-start",
            version = "0.0"
    )
)
object Api {
}
@KubernetesApplication(
    name = "quick-start",
    labels = [Label(key = "app", value = "quick-start")],
    ports = [Port(name = "http", containerPort = 8080)],
    livenessProbe = Probe(httpActionPath = "/health/liveness", initialDelaySeconds = 5, timeoutSeconds = 3, failureThreshold = 10),
    readinessProbe = Probe(httpActionPath = "/health/readiness", initialDelaySeconds = 5, timeoutSeconds = 3, failureThreshold = 10)
)
@EnableServiceMonitor(port = "http", path="/prometheus")
object Dekorate {
}
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("io.micronaut")
		.start()
}

