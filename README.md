# Apache Camel JSON Transformer (JSONata + JOLT)

A Spring Boot + Apache Camel project that transforms
JSON payloads using **JSONata** and **JOLT**, selectable at runtime.

## Why this project exists

- Compare JSONata vs JOLT in real transformations
- Handle complex mappings with aggregation, conditions, and restructuring
- Demonstrate clean Camel route design
- Showcase enterprise-grade JSON transformation patterns

## Tech Stack

- Java 17
- Spring Boot
- Apache Camel
- JSONata (IBM jsonata4java)
- JOLT
- Maven

## Architecture

Client → REST API → Camel Route → Transformer Engine → Response


## How transformation is selected

| Header | Description |
|-----|-----------|
| `X-TRANSFORMER` | `jsonata` or `jolt` |
| `X-MAPPING-NAME` | Mapping file name |

## Sample Request

```http
POST /api/transform
X-TRANSFORMER: jolt
X-MAPPING-NAME: source-to-target.json
Content-Type: application/json
```

## Sample Response

See samples/output for example outputs.

## When to use JSONata vs JOLT

| Use Case | JSONata | JOLT |
|----------|---------|------|
| Complex transformations with conditions, aggregations | ✅ | ❌ |
| Simple structural transformations | ❌ | ✅ |
| Readability and maintainability | ✅ | ❌ |
| Performance for large payloads | ❌ | ✅ |
| Learning curve | Steeper | Gentler |
| Community support | Smaller | Larger |
| Integration with other tools | Limited | Extensive |
| Flexibility in transformation logic | High | Moderate |
| Error handling and debugging | Better | Basic |
| Support for dynamic transformations | Yes | Limited |

## Running the Project
1. Clone the repository
   ```bash
   git clone 
    ```
2. Navigate to the project directory
   ```bash
   cd camel-json-transformer
   ```
3. Build the project
   ```bash
   mvn clean install
   ```
4. Run the Spring Boot application
   ```bash
    mvn spring-boot:run
    ```
5. Send requests to `http://localhost:8080/api/transform`
6. Use headers to select transformer and mapping
7. Check logs for transformation details
8. Review sample inputs/outputs in `samples` directory