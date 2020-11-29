package com.wz.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.wz"])
@SpringBootApplication
class WzGatewayApplication

fun main(args: Array<String>) {
    runApplication<WzGatewayApplication>(*args)
}
