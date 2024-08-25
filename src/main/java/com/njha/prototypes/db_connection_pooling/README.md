# Database connection pooling prototype

This prototype exemplifies how db connection pooling improves system performance and avoids database connection overload issue.

Example 1 - with no connection pooling.

Example 2 - with a custom connection pooling.

### 1. With no connection pooling:
- For each db call, the application establises a new db connection, which invloves -
  - A 3 way handshake to establish the connection
  - A 2 way request-response - query execution (the actual work)
  - And finally a 2 way tear down

### 2. With a custom connection pooling
- Implements a custom connection pool class instead of using Spring Hikari pool connection
- For each db call , it simply gets a connection object from the head of the blocking queue
- Executes the query using that connection object, and then puts the connection object back into the blocking queue
- This way we avoid the "3 way handshake to establish the connection" and the "2 way tear down", on each query execution, which improves application performance.
- By using blocking queue for connection pool implementation and having a predefined fixed size of the pool, it makes sure that the application will never initiate more than 'x' number of connections with the database at any point in time (where 'x' is an application config), thereby avoiding database connection overload issues.


## Observations: 
- I'm running a 10ms sleep query in each thread.
- Without connection pooling(ex-1) DB overload occurs when I fire about 35000 threads concurrently.
Below is the error you get at 35K concurrent requests when using no connection pooling

### MySQL Connection overload error -

<img width="1752" alt="image" src="https://github.com/user-attachments/assets/eabd49f6-08cd-4ba9-9b98-bf1e4c934792">

### PostgreSQL Connection overload error -
<img width="1752" alt="image" src="https://github.com/user-attachments/assets/749b5493-81f6-4ff7-972d-9eea6087061d">

- For 30000 (or lesser) number of threads, ex-2 (with connection pooling) outperforms ex-1 (without connection pooling) when using MySQL
- However, no performance improvement is seen when using PostgreSQL [infact ex2 slightly outperformed ex-1 in some cases], probably because of Postgre's internal optimizations. For the same size of threads, with and without the pooling seems to have nearly similar performance using PostgreSQL.
- Both databases however fails at a certain threshold (35K concurrent requests)
- Above observations occur on my local machine when I'm running a 10ms sleep query in each thread. The sleep query is used to simulate a typical query execution time. The results might vary based on the query execution time.


