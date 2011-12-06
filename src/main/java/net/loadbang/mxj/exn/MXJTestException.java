//	$Source$
//	$Id$

package net.loadbang.mxj.exn;

/**	Generic exception for MXJ test code.
 	TODO: we need some categories of exception: configuration, IO, under/overflow
 	etc.
 */

public class MXJTestException extends Exception {
    private String itsHint;
    private Exception itsCause00;

    /** Constructor.
     * 
     *  @param hint A string describing the originating error location or reason.
     */

    public MXJTestException(String hint) {
        itsHint = hint;
        itsCause00 = null;
    }

    /** Constructor (when there is a nested exception).
     * 
     *  @param hint A string describing the originating error location or reason.
     *  @param cause The nested exception.
     */

    public MXJTestException(String hint, Exception cause) {
        itsHint = hint;
        itsCause00 = cause;
    }

	public Exception getCause00() {
		return itsCause00;
	}

	public String getHint() {
		return itsHint;
	}
}
