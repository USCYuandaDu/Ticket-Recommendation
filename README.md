# Ticket-Recommendation

# Introduction



This is a recommendation web engine for user to search the tickets, user can add likes for this item, based on their likes and geo location, in this way, users can get recommendations about which items to choose.

Used J2EE, Tomcat, Eclipse to build the website, Used MySQL and MongoDB to store the data. And designed recommendation system based on Collaborative Filtering algorithm.

# Installation

1. Install Java 8 
(It MUST BE at least Java 8, not Java 6, not Java 7)
Open this page:  http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. Install Eclipse for Java EE, not Eclipse for Java. 
3. Install Apache Tomcat
4. Install Postman
5. Install MAMP

# Rest

REST stands for Representational State Transfer. It relies on a stateless, client-server, cacheable communications protocol -- and in virtually all cases, the HTTP protocol is used. Roy Fielding developed the REST architectural style in parallel with HTTP 1.1 of 1996–1999, based on the existing design of HTTP 1.0 of 1996.

REST is an architecture style for designing networked applications. The idea is that, rather than using complex mechanisms such as CORBA, RPC or SOAP to connect between machines, simple HTTP is used to make calls between machines.

### six constraints are:
1. Uniform Interface
  It simplifies and decouples the architecture, which enables each part to evolve independently.
  Stateless
2. Each request from client to server must contain all of the information necessary for the server to understand the request, and cannot take advantage of any stored context on the server. Session state is therefore kept entirely on the client.
3. Cacheable
4. Client-Server
5. Layered System
# DataBase

Four tables in our MySql:
1. users - store user information.
2. items - store item information.
3. history - store user favorite/purchase history (many-to-many).
4. category - store item-category relationship (many-to-many).
# Algorithm

## 1. Content-based Recommendation:
Given item profiles (category, price, etc.) of your favorite, recommend items that are similar to what you liked before. 
## 2. Collaborative Filtering:
* User-based Recommendation
Filter based on the similarity of Users. 
User A likes Item A, Item C.
User B likes Item B.
User C likes Item A, Item C, Item D.
=> User A shares similar preference as User C compared to User B. 
User C also likes Item D
=> User A may like Item D. 
* Item-based Recommendation
Item A is liked by User A, User B, User C
Item B is liked by User B.
Item C is liked by User A, User B
=> Item A and Item C are alike
Item C is liked by users who like Item A, so recommend it to user C (another user who likes Item A). 

## Primitive Design:
1. fetch all the events (ids) this user has visited. (which table? which API?)
```java 
history: history_id, user_id, item_id, last_favor_time.
Set<String> itemIds = getFavoriteItemIds(userId);
```
2. given all these events, what are the categories? (which table? which API?)
```java
categories: item_id, category.
Set<String> categories = getCategories(itemId);
```
3. given these categories, what are the events that belong to them (which table? which API?)
```java
external API
List<Item> items = searchItems(userId, lat, lon, category);
```
4. filter events that this user has visited with geo and other information

## MapReduce Design:
A MapReduce program is composed of a Map() procedure (method) that performs filtering and sorting (such as sorting students by first name into queues, one queue for each name) and a Reduce() method that performs a summary operation (such as counting the number of students in each queue, yielding name frequencies).

One of the key challenge is to calculate similarity between users. We will use MapReduce to calculate it. 
### Algorithm description:
Let the targeting user to be user_1 and assume the document is in a format of [user_id, item_id, rating]
user_id, targeting_user_id, 
```java
function map(String user_id, String item_id, Integer rating):
  If user_id has the same rating on the same item_id as user_1:
    emit (user_id, 1)

function reduce(String user_id, Iterable<Integer> values):
  sum = 0
  for value : values:
    sum += value
  emit (user_id, sum)
```
It will actually output a map with the key as user_id and the value is how many items this user has the same rating as user_1. We use this score to recommend items. 




