#!/bin/bash

kotlinc -classpath kotlin-script-resources.jar -script ExampleScript.kts -- "$@"
