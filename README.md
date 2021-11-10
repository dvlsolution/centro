# Centro Ad Placement Exercise

Centro’s core business is the planning and management of display advertisements
on the web. This exercise uses examples from our day-to-day business to
exercise your modeling skills.

During your interview at Centro, you will be asked to refactor and expand on
this code. **We will be evaluating solutions for clarity, simplicity and an
approach that demonstrates a command of object-oriented design principles.**
_Hint: you will be receiving new requirements for this problem in your next
round, so allowing your code to be extended will help you succeed._

## Avoiding Bias

In order to avoid unconscious bias when evaluating code submissions, Centro
anonymizes your work by removing names and email addresses from submitted git
bundles. However, some tools and frameworks include an `author` field in
metadata files (e.g. Node's `package.json` or Ruby's `.gemspec` files), or
include the author's name as part of the directory structure, as in Java
packages.

Please help us keep the submissions bias-free by leaving `author` information
blank in any metadata files included in your submission, and avoid including
any other potentially identifying information, such as Java package names.

## Vocabulary

**Ad placement** (or ad): The advertisement itself, in "place" on a website (or
in an app, or on YouTube). An ad placement runs for a specific period of time,
in a specific context. For example, the time period might be "labor day
weekend" or "spring 2016". The context could be "the sports section of the Des
Moines Register" or "during YouTube videos being displayed to people in Los
Angeles between the ages of 21 and 35 who are interested in sportsball".

**Cost per Mille (Thousand)**: (or **CPM**) The price paid by the buyer of an
ad per 1,000 views of the ad, or "impressions".

**Impressions** one impression is one viewer seeing the ad in place

## Coding Exercise

Please complete the following exercise in any programming language or framework
you feel appropriate, but keep in mind we may ask you to explain or extend your
code. We are a Ruby/Javascript/Java/Python shop so those are our favored
languages, but we are happy to read good code in any language.

Also, please write automated tests and include them with your submission.

In addition to your code and tests, please also include a README that explains:

1. An overview of your design decisions
2. Why you picked the programming language/framework you used
3. How to run your code and tests, including how to install any dependencies
    your code may have.

Please email your completed work back to Centro as a [git
bundle](https://git-scm.com/docs/git-bundle) file. We'd like to see your
process as you work through the problem so commit as you go, providing
informative commit messages.

## Delivery Parsing Problem

1. Write a program that will read the included `placements.csv` and
`delivery.csv` data files to instantiate Placement and Delivery models, and
generate a report containing the total number of impressions delivered and
final cost of each placement. The output should be as follows:

    ```
    Sports (11/1/2020-11/30/2020): 1,083,576 impressions @ $5 CPM = $5,418
    Business (12/1/2020-12/31/2020): 1,607,958 impressions @ $8 CPM = $12,864
    Travel (11/1/2020-11/30/2020): 1,035,966 impressions @ $3 CPM = $3,108
    Politics (12/1/2020-12/31/2020): 1,529,821 impressions @ $6 CPM = $9,179
    ```

2. Expand your program to calculate the number of impressions delivered and
cost for an arbitrary range of days within the provided data set. For example,
11/22/2020-12/5/2020. The output should be as follows:

    ```
    Total (11/22/2020-12/5/2020): 1,126,785 impressions, $6,061
    ```
