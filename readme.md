# Currency Converter Application

## How to run
<code>gradle run</code> would bring up both User window and Admin window. Close either window would terminate the applciation. 

*Note: An error "No X11 DISPLAY variable was set, but this program performed an operation which requires it" might occur on UNIX os if UNIX DISPLAY environment is not set up properly.*

### User window
- Currency conversion
    - left country: convert from
    - right country: convert to
- Reload popular currency table
    - User needs to manually click "reload" button to check whether any popular currency rates have been updated by Admin
    - an up/down arrow will appear next to the currency rate value if the rate increases or decreases
- Generate summary of the conversion rates of 2 currencies within a specific duration (start and end dates)
    - includes all conversion rates, average, median, max, min and SD


### Admin window
*Admin can perform any action User can do*
- Add rate
    - Country currencies are case-insensitive
    - Rates of existing countries would update to its lastest
    - Rates of new countries would create a new column/row in the table
    - Rates of new countries will only be added if no currency in the from input already exists
    - When a rate is successfully added, reload to see the result
- Add/Delete popular currencies
    - Admin selects country they wish to add/delete from the popular currencies table and update it by pressing add/delete button accordingly
    - Reload to see results
    - Popular currencies has upper limit of 4. If there are 4 popular currencies, delete one before adding another


## How to test
- Run <code>gradle test</code> (will auto run <code>gradle test jacocoTestReport</code>) for manual testing.
- Active Jenkins would receive push requests and auto build including tests and generate coverage report.    

## How to contribute to the codebase
- Create a separate branch for Features/Fixes
    - Write unit tests asap if adding a new function  
- Ensure all tests are passed and build successful locally before pushing and requesting to merge
- Ensure the host is running Jenkins before merge to master 
