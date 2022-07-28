# Twitterbot
<img src="https://github.com/botong516/Personal-Java-Projects/blob/main/Twitterbot/Twitterbot.jpeg" width="400" height="280" />   
This project builds a machine learning model and creates an AI that generates (somewhat) realistic tweets!   The general structure of the project:   

- **Training Data** - In this project, the training data is collections of tweets obtained from real twitter data.   
- **Data Cleaning** - For this tweet bot, the training data will be hundreds of tweets from a real twitter account. Using our understanding of what these tweets look like and how our TwitterBot will generate new tweets, we can improve the bot’s performance by filtering and cleaning the raw tweets, yielding ‘cleaned’ training data with which to build the model. For example, we don’t want any URLs to show up in our processed data so we filter out all the tweets that contain URLs.   
- **Regular Expression** - To filter out undesirable tweets, we specify patterns of string data that are considered “bad”. A *regular expression* is a simple way to define such patterns.   
- **Markov Chain** - The specific model we will use in this project is a Markov Chain. To train our Markov Chain, we’ll pass in tweets as sequences of words, which will then be analyzed in pairs so that our model can figure out which words should follow which other words.   

<img src="" width="400" height="280" />
