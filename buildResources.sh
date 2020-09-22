#!/bin/bash

# compile custom Kotlin dependencies required to run scripts
kotlinc Args.kt ScriptUtil.kt ReflectionUtil.kt -d kotlin-script-resources.jar
