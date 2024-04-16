# Statistics JSON Parser
* [Task issued](https://docs.google.com/document/d/18uO9S6XydsqntCqCpOL_z67-fEznwIvCPWUVqRW6pE8/edit?hl=ru)
---
This console application parses a list of JSON files
related to the main entity and generates statistics based on one of its attributes.
---
### Usage

Run 
- AppStatisticJSONParser.class

Path to JSON files:
- src/main/resources/json_files

Results files path
- hw_01_statistic_json_parser/src/main/resources/json_files

If needed you can change field **_ATTRIBUTE_** in AppStatisticJSONParser.class
### Input Files

The JSON files should be structured as follows:

```json
[
  {
    "title": "1984",
    "author": {
      "first_name": "George",
      "last_name": "Orwell"
    },
    "year_published": 1949,
    "genre": ["DYSTOPIAN", "POLITICAL_FICTION"]
  },
  {
    "title": "Pride and Prejudice",
    "author": {
      "first_name": "Jane",
      "last_name": "Austen"
    },
    "year_published": 1813,
    "genre": ["ROMANCE", "SATIRE"]
  }
]
```
### Output

    The application generates an XML file with statistics sorted by count in descending order. 
    The filename will be statistics_by_{attribute}.xml.

Example output for statistics_by_genre.xml:
```xml
<statistics>
  <item>
    <value>Romance</value>
    <count>2</count>
  </item>
  <item>
    <value>Satire</value>
    <count>1</count>
  </item>
  ...
</statistics>

```
### Testing

    Unit tests are provided for parsing JSON filesand generating statistics.
![tests](https://github.com/KorbutViacheslav/profitsoft/blob/main/hw_01_statistic_json_parser/image/tests_statistic_json_parser.jpg?raw=true)
### Experiment Results

    The application was tested with different numbers of threads for parsing files (2, 4, 8).

Results of the experiments:
* Time taken: 189 milliseconds with 2 threads.
* Time taken: 222 milliseconds with 4 threads.
* Time taken: 200 milliseconds with 8 threads.

### Summary

---
Upon completing the project, the program generates an XML file with statistics sorted by quantity from highest to lowest. Using CompletableFuture for parsing ensures efficient thread utilization. Experiments have shown that varying the number of threads has little impact on program execution time. This could be due to insufficient system load or a small amount of data being processed during parsing. Additionally, it's possible that file parsing operations are not sufficiently complex or time-consuming to demonstrate significant benefits from parallel execution across multiple threads.

Overall, this project enables efficient data processing and analysis through parallel processing using CompletableFuture and optimization of thread count to achieve maximum productivity. Furthermore, it's worth noting that unit tests for file parsing logic and statistics generation were written and successfully passed to ensure program reliability and quality.

---
