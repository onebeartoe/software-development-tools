
install.packages("tidyverse")
install.packages("ggplot2")

library(tidyverse)
library(ggplot2) 

titanicData <- read.csv("titanic-passenger-list.csv")

dim(titanicData)

summary(titanicData)

ggplot(data=titanicData, aes(x=age)) +
  geom_histogram(fill="steelblue", color="black") +
  ggtitle("Histogram of Age Values")

# I could not get this next graph to work
#ggplot(data=titanicData, aes(x=sex)) +
#  geom_histogram(fill="steelblue", color="black") +
#  ggtitle("Histogram of Sex Values")

