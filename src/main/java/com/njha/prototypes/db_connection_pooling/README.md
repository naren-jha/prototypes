# Database connection pooling prototype

This prototype exemplifies how db connection pooling improves system performance and avoids database connection overload issue.

Example 1 - with no db pooling.
Example 2 - with custom db pooling.

### With no db pooling:
- For each db call, we create a new connection, which invloves
- A 3 way handshake to establish the connection
- A 2 way request-response - query execution (the actual work)
- And finally a 2 way tear down

### With custom db pooling
- Implements a custom connection pool class instead of using Spring Hikari pool connection
- for each db call , it simply gets a connection object from the head of the blocking queue
- executes the query and then puts the connection object back into the blocking queue
- This way we avoid the "3 way handshake to establish the connection" and the "2 way tear down", which adds to application performance.
- By using blocking queue for connection pool implementation to allocate connection objects to the clients, it makes sure that the application will never initiate more than 'x' number of connection with the database ('x' is an application config), thereby avoiding database connection overload issues.

Below is the error you get when using example 1 (With no db pooling)

MySQL Connection overload 
<img width="1752" alt="image" src="https://github.com/user-attachments/assets/eabd49f6-08cd-4ba9-9b98-bf1e4c934792">

PostgreSQL Connection overload
<img width="1752" alt="image" src="https://github.com/user-attachments/assets/749b5493-81f6-4ff7-972d-9eea6087061d">

Further observations: 
- Performance improvement is not seen in PostgreSQL, probably because of Postgre's internal optimizations. For the same size of threads, with and without the pool seems to have similar performance.
- Postgre has more user friendly error messages :)


