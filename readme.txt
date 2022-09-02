Here is a brief overview of the variables in App and how they are intended to be used.
    initialCurrencies: Stores initial 6 countries and is used just for initialization of system. DO NOT TOUCH.
    initialRates: Same as countries but for rates. DO NOT TOUCH.
    initialRatesMap: Correlates the above. DO NOT TOUCH.
    dir: variable used to create new files. DO NOT CHANGE.
    currencyHist: Acts as a way to retrieve file for currency history.
                  Key set is the currency code for a country
                  Values are created file streams
                  Some example workflows:
                       Admin wants to add a price to an existing country
                            file = currencyHist.get(country)
                            write some new price to file
                       Admin wants to add a price to a not yet existing country
                            file = new File(dir, "<country currency code>") -> use this format exactly so file is in right place.
                            currencyHist.put("<country currency code>", file) -> can now use this to write to