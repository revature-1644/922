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