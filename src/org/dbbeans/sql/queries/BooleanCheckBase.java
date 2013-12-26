package org.dbbeans.sql.queries;

import org.dbbeans.sql.DBQuerySetupProcess;

/**
 * Base class for...
 */
abstract class BooleanCheckBase implements DBQuerySetupProcess {
    protected boolean answer = false;

    public boolean isTrue() {
        return answer;
    }
}
