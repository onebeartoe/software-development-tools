
install.packages("cowplot")
install.packages("kableExtra")

library(cowplot)
library(dplyr)
library(ggplot2)
library(magrittr)
library(kableExtra)
library(knitr)
library(readxl)
library(tidyverse)


summary_stats <- read.csv("hw2_DataSet.csv")

RandomA_Mean_Formatted <- mean(summary_stats$random_A)
RandomB_Mean_Formatted <- mean(summary_stats$random_B)
RandomC_Mean_Formatted <- mean(summary_stats$random_C)
RandomD_Mean_Formatted <- mean(summary_stats$random_D)

RandomA_Median_Formatted <- median(summary_stats$random_A)
RandomB_Median_Formatted <- median(summary_stats$random_B)
RandomC_Median_Formatted <- median(summary_stats$random_C)
RandomD_Median_Formatted <- median(summary_stats$random_D)

RandomA_FirstQuartile_Formatted <- quantile(summary_stats$random_A, 0.25)
RandomB_FirstQuartile_Formatted <- quantile(summary_stats$random_B, 0.25)
RandomC_FirstQuartile_Formatted <- quantile(summary_stats$random_C, 0.25)
RandomD_FirstQuartile_Formatted <- quantile(summary_stats$random_D, 0.25)

RandomA_ThirdQuartile_Formatted <- quantile(summary_stats$random_A, 0.75)
RandomB_ThirdQuartile_Formatted <- quantile(summary_stats$random_B, 0.75)
RandomC_ThirdQuartile_Formatted <- quantile(summary_stats$random_C, 0.75)
RandomD_ThirdQuartile_Formatted <- quantile(summary_stats$random_D, 0.75)

myTable <- data.frame(
  Group = c("RandomA", "RandomB", "RandomC", "RandomD"),
  Mean = c(RandomA_Mean_Formatted, RandomB_Mean_Formatted, RandomC_Mean_Formatted, RandomD_Mean_Formatted),
  Median = c(RandomA_Median_Formatted, RandomB_Median_Formatted, RandomC_Median_Formatted, RandomD_Median_Formatted),
  First_Quartile = c(RandomA_FirstQuartile_Formatted, RandomB_FirstQuartile_Formatted, RandomC_FirstQuartile_Formatted, RandomD_FirstQuartile_Formatted),
  Third_Quartile = c(RandomA_ThirdQuartile_Formatted, RandomB_ThirdQuartile_Formatted, RandomC_ThirdQuartile_Formatted, RandomD_ThirdQuartile_Formatted)
)


# Table Summary
kable(myTable, format = "html", table.attr = 'class="table"') %>%
  kable_styling(bootstrap_options = "striped", full_width = FALSE)




# 1D Scatter Plot
create_strip_chart <- function(data, column_name) {
  ggplot(data, aes(x = 1, y = !!sym(column_name))) +
    geom_jitter(position = position_jitter(width = 0.1), color = "black", alpha = 0.7) +
    geom_hline(yintercept = mean(data[[column_name]], na.rm = TRUE), color = "black", linetype = "solid", size = 1) +
    geom_hline(yintercept = median(data[[column_name]], na.rm = TRUE), color = "green", linetype = "dashed", size = 1) +
    geom_hline(yintercept = quantile(data[[column_name]], 0.25, na.rm = TRUE), color = "blue", linetype = "dashed", size = 1) +
    geom_hline(yintercept = quantile(data[[column_name]], 0.75, na.rm = TRUE), color = "blue", linetype = "dashed", size = 1) +
    theme_minimal() +
    labs(title = paste("1-D Scatter Plot -", column_name),
         x = NULL, y = "Values") +
    theme(axis.text.x = element_blank(), axis.ticks.x = element_blank())  # Remove x-axis labels and ticks
}


# Box Plot
create_box_plot <- function(data, column_name) {
  ggplot(data, aes(x = "", y = !!sym(column_name))) +
    geom_boxplot(color = "black", fill = "white", alpha = 0.7) +
    geom_hline(yintercept = mean(data[[column_name]], na.rm = TRUE), color = "black", linetype = "solid", size = 1) +
    geom_hline(yintercept = median(data[[column_name]], na.rm = TRUE), color = "green", linetype = "dashed", size = 1) +
    geom_hline(yintercept = quantile(data[[column_name]], 0.25, na.rm = TRUE), color = "blue", linetype = "dashed", size = 1) +
    geom_hline(yintercept = quantile(data[[column_name]], 0.75, na.rm = TRUE), color = "blue", linetype = "dashed", size = 1) +
    theme_minimal() +
    labs(title = paste("Box Plot -", column_name),
         x = NULL, y = "Values") +
    theme(axis.text.x = element_blank(), axis.ticks.x = element_blank())  # Remove x-axis labels and ticks
}



# Historgram
create_histogram <- function(data, column_name) {
  ggplot(data, aes(x = !!sym(column_name))) +
    geom_histogram(color = "black", fill = "white", alpha = 0.7, bins = 30) +
    geom_vline(xintercept = mean(data[[column_name]], na.rm = TRUE), color = "black", linetype = "solid", size = 1) +
    geom_vline(xintercept = median(data[[column_name]], na.rm = TRUE), color = "green", linetype = "dashed", size = 1) +
    geom_vline(xintercept = quantile(data[[column_name]], 0.25, na.rm = TRUE), color = "blue", linetype = "dashed", size = 1) +
    geom_vline(xintercept = quantile(data[[column_name]], 0.75, na.rm = TRUE), color = "blue", linetype = "dashed", size = 1) +
    theme_minimal() +
    labs(title = paste("Histogram -", column_name),
         x = "Values", y = "Frequency") +
    theme(axis.text.x = element_blank(), axis.ticks.x = element_blank())  # Remove x-axis labels and ticks
}


# Create the three plots for RandomA
plot_randomA_strip <- create_strip_chart(summary_stats, "random_A")



plot_randomA_box <- create_box_plot(summary_stats, "random_A")
plot_randomA_histogram <- create_histogram(summary_stats, "random_A")

# Arrange plots in a grid for RandomA
grid_randomA <- plot_grid(
  plot_randomA_strip, plot_randomA_box, plot_randomA_histogram,
  nrow = 1, align = 'h', rel_widths = c(1, 1, 1)
)

# Print the grid for RandomA
print(grid_randomA)
