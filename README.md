# Spring Boot Microservice Template

Production-ready Spring Boot microservice template optimized for Kubernetes deployment.

## Tech Stack

- **Java 21** (LTS)
- **Spring Boot 3.4.1**
- **Maven 3.9+**
- **Docker** (multi-stage build)
- **Kubernetes** (with Kustomize)

## Project Structure

```
├── src/
│   └── main/
│       ├── java/              # Application code
│       └── resources/
│           ├── application.yml        # Base config
│           ├── application-stb.yml    # Staging config
│           └── application-prod.yml   # Production config
├── k8s/
│   ├── base/                  # Base K8s manifests
│   │   ├── deployment.yaml
│   │   ├── service.yaml
│   │   ├── configmap.yaml
│   │   ├── hpa.yaml
│   │   └── kustomization.yaml
│   └── overlays/
│       ├── stb/               # Staging overlay
│       └── prod/              # Production overlay
├── Dockerfile
└── pom.xml
```

## Branches

| Branch | Environment | Purpose |
|--------|-------------|---------|
| `main` | - | Development, template source |
| `stb` | Staging | Pre-production testing |
| `prod` | Production | Live environment |

## Quick Start

### Local Development

```bash
# Build
mvn clean package

# Run
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=stb
```

### Docker

```bash
# Build image
docker build -t demo-service:latest .

# Run container
docker run -p 8080:8080 demo-service:latest

# Run with environment
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=stb demo-service:latest
```

### Kubernetes Deployment

```bash
# Deploy to staging
kubectl apply -k k8s/overlays/stb

# Deploy to production
kubectl apply -k k8s/overlays/prod

# Check deployment
kubectl get pods -l app=demo-service
```

## Endpoints

| Endpoint | Description |
|----------|-------------|
| `GET /actuator/health` | Health check |
| `GET /actuator/health/liveness` | Liveness probe (K8s) |
| `GET /actuator/health/readiness` | Readiness probe (K8s) |
| `GET /actuator/prometheus` | Prometheus metrics |
| `GET /actuator/info` | Application info |

## Kubernetes Features

### Best Practices Included

- **Health Probes**: Liveness and readiness probes configured
- **Resource Limits**: CPU/Memory requests and limits
- **HPA**: Horizontal Pod Autoscaler based on CPU/Memory
- **PDB**: Pod Disruption Budget (prod only)
- **Graceful Shutdown**: 30s termination grace period
- **Pod Anti-Affinity**: Spread pods across nodes (prod)
- **Non-root User**: Container runs as non-root
- **Prometheus Metrics**: Built-in metrics endpoint

### Environment Differences

| Feature | STB | PROD |
|---------|-----|------|
| Replicas | 2-5 | 3-20 |
| Memory Limit | 512Mi | 1Gi |
| CPU Limit | 500m | 1000m |
| Log Level | DEBUG | INFO |
| PDB | No | Yes (min 2) |

## Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | default |
| `JAVA_OPTS` | JVM options | -XX:MaxRAMPercentage=75.0 |

### Adding Secrets

For sensitive data, create a Kubernetes Secret:

```bash
kubectl create secret generic demo-service-secrets \
  --from-literal=DB_PASSWORD=your-password \
  -n <namespace>
```

## CI/CD Integration

### GitHub Actions Example

```yaml
- name: Build and Push
  run: |
    docker build -t $REGISTRY/demo-service:${{ github.sha }} .
    docker push $REGISTRY/demo-service:${{ github.sha }}

- name: Deploy to STB
  if: github.ref == 'refs/heads/stb'
  run: |
    kustomize edit set image demo-service=$REGISTRY/demo-service:${{ github.sha }}
    kubectl apply -k k8s/overlays/stb
```

## Creating a New Service

1. Use this template: `gh repo create my-service --template ahmedgeze/spring-boot-template`
2. Update `artifactId` and `name` in `pom.xml`
3. Rename package from `com.example.demo` to your package
4. Update `spring.application.name` in `application.yml`
5. Update K8s manifests with your service name

## License

MIT
