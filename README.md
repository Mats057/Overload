# Overload Findings

This repository explores several approaches to handling high load and throughput in a Spring Boot application.

It is a simple REST API designed to handle heavy data loads while dealing with different bottlenecks, such as request rate, success rate, CPU load, HTTP request overhead, DB transaction cost, and more.

## Async is not a silver bullet

Moving the heavy job processing to `@Async` helps reduce request latency when the work itself is expensive, but it does not automatically increase throughput when the real constraint is request volume, database writes, thread pools, or client/server saturation.

## New HTTP requests have a cost

When the system is under heavy request load, the cost of creating new HTTP requests can be significant.

Using k6 for load testing, it became clear that creating a new request every time can itself become the bottleneck when the load increases.

## A system can keep a high accept rate and still fail requests

The endpoint can return `202 Accepted` for most calls while some requests still fail because of downstream saturation, connection aborts, or other runtime bottlenecks.
