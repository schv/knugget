# KNugget
## Stage 1: Nugget Extractor

### [Presentation](https://docs.google.com/presentation/d/1BLLI9ZtqXI3VuAHjF5-_B1zFylD7JeU_RJJ1ucQRboA/edit?usp=sharing)

This project attempts to utilize LLMs for the task of "nugget extraction" where nuggets are:
- Fact references
- Value judgements
- Quotes

These can be further used for comparative analysis between sources and many other things.

## Building
Just make Gradle do the thing with `build.gradle.kts`.

Runs on [Koog](https://github.com/JetBrains/koog) agent framework by JetBrains

## Launching
Run `Main.kt` passing the `OPENAI_API_KEY` as an environment variable.  
The articles are hardcoded into the `Article.kt` as of now.  
Results can be found in the `samples` directory.