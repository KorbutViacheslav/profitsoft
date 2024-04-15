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
- src/main/resources/statistic_results

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

### Experiment Results

    The application was tested with different numbers of threads for parsing files (2, 4, 8).

Results of the experiments:
* Time taken: 189 milliseconds with 2 threads.
* Time taken: 222 milliseconds with 4 threads.
* Time taken: 200 milliseconds with 8 threads.