## An overview of your design decisions
Initial design is to create a model structure and parse input files into the memory,
then operate with model and input options print one or another report. Solution has
some limitations and trade off, for instance it needs additional memory to store
parsed files that works well for small files, but in case of huge files another approach
should be considered, for instance calculate output on the fly when parsing input files.

## Why you picked the programming language/framework you used
Program designed as a simple CLI application that expects some input parameters and
prints a result into console. Java programming language used because I have more
experience using it. Maven framework used for managing 3rd party dependencies, also
it helps to build/package project. A few Apache Commons libraries are helpful to
build CLI application `commons-cli` and read/parse CSV files `commons-csv`.

## How to run your code and tests, including how to install any dependencies your code may have.
Apache Maven should be installed, please follow the instructions on https://maven.apache.org.
Project build, in the command like got ot project directory and run command:
```
mvn clean package
```
After maven builds and packages the project `target` directory should contain the file 
`AdPlacement-1.0-SNAPSHOT-DIST.tar.gz`.
Run project, in command like unpack distribution archive and run
```
./adplacement.sh
```
Without any options it should provide required arguments output
```
Missing required options: p, d
usage: AdPlacement
 -d,--delivery <arg>     delivery input file path (required)
 -e,--end-date <arg>     end search date MM//dd/yyyy (optional)
 -p,--placement <arg>    placement input file path (required)
 -s,--start-date <arg>   start search date MM/dd/yyyy (optional)
```
Example how to run application with provided placements.csv and delivery.csv:
```
./adplacement.sh -d <path to delivery.csv file> -p <path to placement.csv file>
```
expected output (actual execution time may vary):
```
Sports (11/1/2020-11/30/2020): 1,083,576 impressions @ $5 CPM = $5,418
Business (12/1/2020-12/31/2020): 1,607,958 impressions @ $8 CPM = $12,864
Travel (11/1/2020-11/30/2020): 1,035,966 impressions @ $3 CPM = $3,108
Politics (12/1/2020-12/31/2020): 1,529,821 impressions @ $6 CPM = $9,179
Execution time: 49 ms
```
or with date range filter option:
```
./adplacement.sh -d <path to delivery.csv file> -p <path to placement.csv file> -s 11/22/2020 -e 12/5/2020
```
expected output (actual execution time may vary):
```
Total (11/22/2020-12/5/2020): 1,126,785 impressions, $6,061
Execution time: 52 ms
```