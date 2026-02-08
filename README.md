# Catawiki UI Automation Assignment (Playwright + Java + JUnit5)

This repository contains a small, maintainable UI test suite for Catawiki using **Playwright (Java)**, **JUnit 5**, and **Gradle**.  
It covers the required scenario (search → open lot → read & print values) plus a few additional, diverse exploratory tests.

---

## Tech Stack + Set up

- **Java** + **JUnit 5**
- **Playwright for Java**
- **Gradle**
- Page Object pattern + small domain models (e.g., `LotSummary`)
- Built-in Playwright artifacts:(screenshots + snapshots + sources)
- Tests live under `src/uiTest/java/com/catawiki/tests`.


## How to Run

### Prerequisites
- Java (17+ recommended)
- Gradle wrapper included (`./gradlew`)

## Run all UI tests 
```bash
./gradlew uiTest --rerun-tasks
```
Headed browser
No artificial delays
Stable execution against production site

```bash
./gradlew uiTest --rerun-tasks
```
Passing slowMo is Useful for:
visually following the flow
debugging failures
demoing test behavior

## Outputs / Artifacts

After each test run, artifacts are created under:

build/ui-artifacts/

*.png (full-page screenshot per test)

*.zip (Playwright trace per test)

You can open a trace locally with:

npx playwright show-trace build/ui-artifacts/<trace-file>.zip


