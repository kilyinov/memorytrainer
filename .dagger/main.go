package main

import (
	"context"

	"dagger/memorytrainer/internal/dagger"
)

type Memorytrainer struct{}

// Build runs a Maven build and returns the Spring Boot JAR.
func (m *Memorytrainer) Build(
	ctx context.Context,
	// +defaultPath="/"
	// +ignore=["target", ".git", "data"]
	source *dagger.Directory,
) *dagger.File {
	mavenCache := dag.CacheVolume("maven-repo")

	return dag.Container().
		From("maven:3.9-eclipse-temurin-17").
		WithMountedCache("/root/.m2", mavenCache).
		WithDirectory("/app", source).
		WithWorkdir("/app").
		WithExec([]string{"mvn", "-B", "clean", "package", "-DskipTests"}).
		File("/app/target/memorytrainer-0.0.1-SNAPSHOT.jar")
}
