
install.packages("tidyverse")


library(tidyverse)


titanicData <- read.csv("titanic-passenger-list.csv")

dim(titanicData)

ggplot(data=titanicData, aes(x=age)) +
  geom_histogram(fill="steelblue", color="black") +
  ggtitle("Histogram of Age Values")
