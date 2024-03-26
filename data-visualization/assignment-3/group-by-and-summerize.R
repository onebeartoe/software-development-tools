# Q1  Stubbed out solution
# This assumes you already have a ./datafiles folder.  Feel free to check for 
# this and create it automatically if you wish
if (!file.exists("./datafiles/cc-est2022-alldata.pdf")) {
  url <- "https://www2.census.gov/programs-surveys/popest/technical-documentation/file-layouts/2020-2022/cc-est2022-alldata.pdf"
  download.file(url,"./datafiles/cc-est2022-alldata.pdf", mode = "wb")
}


if (!exists("popSubset")) { # Check if the object is not already loaded
  if (file.exists("./datafiles/popSubset.rdata")) {
    load("./datafiles/popSubset.rdata")
  } else {
    if (!file.exists("./datafiles/cc-est2022-all.csv")) {
      url <- "https://www2.census.gov/programs-surveys/popest/datasets/2020-2022/counties/asrh/cc-est2022-all.csv"
      download.file(url,"./datafiles/cc-est2022-all.csv")
    } 
    popAllData <- read_csv("./datafiles/cc-est2022-all.csv") # %>%

summarise(popAllData)        
    
    # This is where you will have to select only the rows 
    # with no missing values and only the required columns
    # What I've done is take just 2 rows and 5 columns.
    # You need to check for complete.cases() and use the dplyr select() command
    popSubset <- popAllData[1:836000, 1:7]
    save(popSubset, file="./datafiles/popSubset.rdata" )
  }
}
dim(popSubset) # 2 rows X 5 columns, you'll want 836000 rows X 7 columns

summarize(popSubset)
