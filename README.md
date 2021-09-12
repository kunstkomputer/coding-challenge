### coding challenge

I took two different slightly different approaches in the two different languagess. The python implementation writes the downloaded csv document to disk first and processes them. The processed reuslt is also written to disk before its uploaded to the webservice.
I call it the "file buffered" approach.
The Java application on the other Hand is completly Stream based and the processing and downloading all takes place in mem. Quick runs showed that the Java version of storing everything in Memory is hogging quite some Ram (for 1.000.000 rows about 1GB).
For production use with n>10000 lines to fetch, I would tend to use the file buffered approach and would modify my java code.

