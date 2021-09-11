### Asumptions

* the float value of the articles is only used to determine the cheapest price. No further calculations are erequired in the future. If this changes, employ a proper money handling class.
* as stated in the task, the order of entries in the csv remains the same after consecutive calls.
* the size of the CSV file fits in Memory during parsing. In case the file/numer of articles gets bigger, it shall be dumped to disk first and read from there with a buffered reader. The processing of the Article List, may also be perfomed in chunks to not bloat the memory. 
