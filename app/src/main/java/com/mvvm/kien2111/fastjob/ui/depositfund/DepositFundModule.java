package com.mvvm.kien2111.fastjob.ui.depositfund;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WhoAmI on 28/04/2018.
 */
@Module
public class DepositFundModule {
    @Provides
    TableExchangeAdapter provideTableExchangeAdapter(DecimalFormat decimalFormat){
        return new TableExchangeAdapter(decimalFormat);
    }

    @Provides
    DecimalFormat provideDecimalFormat(){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        return decimalFormat;
    }
}
