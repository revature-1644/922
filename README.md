REST API Design
Representation State Transfer
- REST API is a set of standards for creating an API that is maintainable, easily understood, and
scalability (we can increase the amound of traffic to a REST api and it handles the increased
performance required in a manageable way)

- Uniform interface
  - A endpoint that we write identifies a resource. 
    - (a URI should only contain nouns in its path)
  - An endpoint should be self-descriptive
    - (The HTTP verb that we use, such as get/post/patch/put/delete fully describes what action you're
      intending to perform)
      - post is for inserting completely new pieces of data
      - put is for completely overwriting a piece of data
      - patch is for partially overwriting a piece of data
  - Data should be transmitted in a way that transfers the state of resource
    - We need to transfer information about our models in a recognizable format such as JSON
    
- Statelessness
  - The purpose of our API is just to transfer information regarding the states of our data between
    the web and your database, or any other external sources. Meaning, it doesn't keep track of any
    data beyond the scope of a web request/session.
    - Maintaining a class-scoped arraylist of flights, paintings, etc would break RESTful convention.
      Because keeping track of data in this temporary format would make it difficult to scale up the
      application.

"Ok... how do I log in...."
- We could break RESTful convention. just send a POST request containing the login information, 
and respond to the login request as either a success 200 (with any security details involved) or
failure (401 unauthorized)
- We could stick to RESTful convention, but we'd need to add in an entirely new model named
"LoginRequest" - which would allow us to POST this new model, and POSTing the new model would initiate
any additional actions the backend needs to perform as a side effect. For instance, registering an
account may require us to POST a RegistrationRequest, which creates a User as a side effect...

Let's say I want to stick to being 100% restful..
"User says " -> I want to be able to reserve a flight online, and I want to be able to check the status
of my flights
User table
Flight table
Reservation table - has to be separate, because many users can belong to many flights..

User's perspective (As a User)
+ POST /UserRegistration (containing the user's registration info, potentially encrypted? 
will create a user model as a side effect)
+ POST /UserLogin (containing the user's login info, potentially encrypted? will return security info)
+ GET /Flight?departure_city=?&arrival_city=? (with query parameters of departure and arrival cities)
+ POST Reservation (containing the reservation info, with fkey values to user and flight)
+ GET /User/{id}/Reservation (get all the reservations of a particular user id)
+ GET /User/{id}/Flight (get all the flights a user has a reservation for)

Airline perspective
+ POST /Flight (add a new flight)
etc...

All of these endpoints, in the real world, would also check an encrypted token to verify that
the user is allowed to access this endpoint...

for now, don't worry about security.

"sublanguages" is the concept that groups of commands within SQL are their own languages
DDL - Data definition language - create table / alter table
DQL - Data query language - select
DML - Data modification language - insert / delete / update
DCL - Data control language - grant / create roles
TCL - Transaction control language - commit - "all or nothing" grouping of sql statements

ACID

a join combines two different tables into a single result set when a value matches
"inner/outer"
inner - excludes results where one of the tables does not have a matching value
outer - includes a single null values when one of the tables has a matching value
"left/right/full"
left - prioritizes the left table, where null values for the left table are excluded
right - same, but for right tables
full - null values for either side are included
"cross join" produces every possible combination of items between two tables (cartesian product)
if we have a product and store table, a cross join would produce a result of every possible product
a store could sell

in most cases, if you just want to match primary/foreign keys, you're just using an inner join
something like an outer join is especially useful when we want to find 'store that does NOT sell
this product' or the 'artist that did NOT paint any paintings' - because we have access to the single
null value

let's say we did a full outer join between artists and paintings
 so, that would retrieve both 1) artists that have no related paintings, so the artist
 would have a single entry with a null set of values for 'painting'
 2) paintings that have a null foreign key to artist table, so no related artist could be found

 maybe we're building a service that allows museum managers to correlate paintings and artists- so,
 we want to build a system that retrieves the as-of-yet unmapped paintings/artists
 
constraints are additional rules applied to the table
EG, primary key / foreign key both represent constraints - primary key non-null, unique
foreign keys have referential integrity constraints
UNIQUE, NON NULL, CHECK 

website.com/api/v1/noun/any other noun
flights.com/api/v2/city/tampa
flights.com/api/v3/flight/01846?queryVar=city=tampa
flights.com/api/v3/flight?departingCity=tampa
flight.com/api/v3/airline/delta/flight
flight.com/api/v3/airline/123/flight
flight.com/api/v3/flight?airline=delta
HTTP request
-verb (action)
URI
headers (metadata/intended to be secure)
body (is also secure, but used more for structured data)