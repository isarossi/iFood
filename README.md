# Music Rwcommendation API

It is a micro-service able to accept RESTful requests receiving as parameter either city name or lat long coordinates. it returns a playlist recommendation according to the current temperature.

Recommendation Case Logic

1. temperature> 30 degrees, suggest tracks for party.
2. 15 <= temperature => 30 degrees, suggest pop music tracks.
3. 10 <= temperature > 15 degrees suggest rock music tracks.
4. temperature < 10  degrees suggest classical music tracks.

The reccomendation is based on the following APIs:

1.https://api.openweathermap.org/data/2.5/weather : It is used to retrieve the temperature.
2.https://accounts.spotify.com//v1/recommendations: Based on the temperature received from OpenWeatherMaps, get reccomendation based on genre.

## Tests Examples

#City

http://localhost:8080/recommendation/city?city=Grytviken
http://localhost:8080/recommendation/city?city=London

#Geocode

http://localhost:8080/recommendation/lat?lat=51.51&lon=-0.13
http://localhost:8080/recommendation/lat?lat=2&lon=-0.13

### Break down into end to end tests