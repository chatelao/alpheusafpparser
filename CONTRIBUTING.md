# Contributing to Alpheus AFP Parser

Thank you for your interest in contributing to the Alpheus AFP Parser! This project aims to provide a robust and compliant parser for the IBM Advanced Function Presentation (AFP) architecture.

## How to Contribute

### Reporting Bugs

- Search the existing issues to see if the bug has already been reported.
- If not, open a new issue with a clear title and description.
- Provide a minimal reproducible example if possible.
- Include information about your environment (OS, Java version).

### Suggesting Features

- Open a new issue to discuss your proposed feature.
- Explain why the feature would be useful and how it aligns with the project goals.

### Pull Requests

1.  **Fork the repository** and create your branch from `master`.
2.  **Ensure Java 8 compatibility**: The project currently targets Java 8.
3.  **Follow coding standards**: Use consistent formatting and naming conventions.
4.  **Add tests**: All new features and bug fixes must include corresponding tests. We are migrating to **JUnit 5 (Jupiter)**, so please use it for new tests.
5.  **Maintain JAXB Annotations**: If you are adding or modifying structured fields, ensure they are correctly annotated with JAXB for XML export. Use `@XmlType(name = "...")` to avoid name collisions.
6.  **Update Documentation**: If your changes affect public APIs or behavior, update the relevant documentation (e.g., `HOWTO.md`, `README.md`).
7.  **Reference the Roadmap**: If your PR addresses a step in `ROADMAP.md`, please mention it in the description.
8.  **Run Tests**: Ensure all tests pass before submitting your PR:
    ```bash
    ./gradlew test
    ```

## Development Environment

- **JDK**: Java 8 (for compatibility) or higher (using Java 8 source/target levels).
- **Build System**: Gradle 8.x.
- **Testing**: JUnit 5 (preferred) and JUnit 4 (legacy).

## Style Guidelines

- Use meaningful variable and method names.
- Keep classes and methods focused and modular.
- Add comments to explain complex logic or architectural decisions.

We look forward to your contributions!
