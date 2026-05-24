# Performance Gain by Switching to Aalto XML

This document summarizes the performance improvements achieved by explicitly enforcing the use of the Aalto XML StAX implementation in `AfpStreamingXmlWriter`.

## Benchmark Environment
- **File Size:** 10MB Synthetic AFP
- **Operation:** Full conversion from AFP to XML using `AfpStreamingXmlWriter` (StAX + JAXB)
- **Tool:** `PerformanceRegressionTest.test10MBConversionPerformance`

## Results

| Implementation | Run 1 | Run 2 | Run 3 | Run 4 | Average |
|----------------|-------|-------|-------|-------|---------|
| Standard StAX (JRE Default) | 1479ms | 1572ms | 1016ms | 1350ms | **1354ms** |
| Aalto StAX (Enforced) | 899ms | 796ms | 1042ms | 880ms | **904ms** |

## Analysis

By explicitly using `com.fasterxml.aalto.stax.OutputFactoryImpl`, we achieved an average speedup of approximately **1.5x (33% reduction in time)** for 10MB file conversions.

Enforcing Aalto ensures that the project consistently utilizes a high-performance, non-blocking XML processor, bypassing the overhead and unpredictability of the standard JRE Service Provider Interface (SPI) discovery mechanism.
