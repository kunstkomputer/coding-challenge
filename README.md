# coding-challenge

Example code that downloads a list of articles and groups them by products, performs transformations and uploads them via csv to remote server.
### How to run
* clone this repo and create a virtualenv for python
* install dependencies via pipenv `pipenv install`
* create a shell in the venv. Either activate venv or run `pipenv shell`
```
❯ python product_list/app.py -h
```

# Testing the test server manually
download a csv file from sample service
```
❯ curl -kv http://localhost:8080/articles/1 -o tmp/one.csv
```


curl to upload a well formatted csv file. --data-binary is important as curl would strip linebreaks otherwise.
```
❯ curl -kv -X PUT http://localhost:8080/products/1 -H "Content-Type: text/csv" -H "Accept:" --data-binary @tmp/upload.csv
```

The argument supplied to the sample service for the download needs to be the same number as supplied with the upload.
Otherwise the call will return `406 Not Acceptable`.

It is _not_ the number of lines in the processed product catalog.
