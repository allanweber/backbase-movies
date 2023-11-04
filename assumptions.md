# Assumptions for the challenge

* My first assumption was regarding the CSV. Since the endpoint regarding Nominee only cared about Best Picture awards, I only leaded this data. So a filter out wat I need from the file.
* I also noticed that the column Nominee did not have always the movie title, so some tricks were needed to get the right data, I created a parser that can be extended depending on the data category.
* I assumed movie titles for the endpoints and not movie id, since would be more difficult to be tested for you.
* I decided to not over load the seed during startup, so I decided to get the box office only when the movie is rated, since this data is not needed until you want to know about the top-rated movies. Of course this means after loading the data the first time, the box office will be empty, so if you request the top-rated movies, the box office will be empty, but if you request the top-rated the return will be there but will not be updated.
* I did not create a way to upload the CSV file. Maybe a huge problem, but who knows :)