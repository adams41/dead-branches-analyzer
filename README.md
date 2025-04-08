Dead Branches Analyzer

Keep your GitHub repos clean! This tool helps you find and clean up inactive branches in your repository. If a branch hasn’t seen any action in a while, this tool will let you know. 🚀

Why You Need It:
Over time, repositories can accumulate old branches that aren't being used anymore. This clutter can make it harder to navigate and maintain your projects. Dead Branches Analyzer automatically helps you identify branches that haven't been updated for a while, so you can clean up your repo and keep it neat and tidy.

Features:
Find inactive branches: See which branches haven’t been touched in months.

Track last commit dates: Get the exact date of the last commit for each branch.

Easy to use: Run the tool and get a quick overview of all branches.

Prerequisites:
1) Java 8 or higher
2) A GitHub personal access token with repo access

Installation
- Clone the repo
- Build the project with mvn clean install
- Set up your GitHub token (make sure it has the right permissions to read your repos)
- Run the app

The tool will fetch your branches and show the last commit date for each. If any branches are inactive for more than 6 months, it will flag them as “dead.”

You can change the inactivity threshold (default is 6 months) by adjusting the code in DeadBranchesAnalyzerApp.java.

Update the repository name in the code to match the one you want to analyze.

