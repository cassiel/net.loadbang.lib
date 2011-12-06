/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

import java.io.*;

/** Global package com.workthing.for Dandelion exceptions. For the most part,
    the exception indicates an internal error. */

public class FAILURE extends Exception {
	/** Unclassified error. (Untidy: we should classify all of them.) */
    public static final int UNDEFINED			=  0;

    /** Bad parameter to servlet, handler, whatever. */
    public static final int BAD_REQUEST			=  1;

//  public static final int FETCH_SQL			=  2;
//  public static final int SQL_OTHER			=  3;

    /** Attempt to render a URI for which no content exists (and
	for which any dynamic renderer has declined to make any). */

    public static final int FETCH_FILE_NOT_FOUND	=  4;


//  public static final int FETCH_IO			=  5;
//  public static final int FETCH_CONTENT		=  6;

    /** Cache I/O error: problem accessing the disk cache, or
	problem using sockets. */

    public static final int CACHE_IO			=  7;

    /** Protocol error between Apache and renderer. */
    public static final int CACHE_PROTOCOL		=  8;

    /** End-of-file encountered by rendering thread. (Benign.) */
    public static final int CACHE_EOF			=  9;

    /** Instantiation failure - class not found ({@link
	dandelion.database.Handler Handler}, {@link DynamicPageMaker
	DynamicPageMaker}). */

    public static final int CLASS_NOT_VALID		= 10;

    /** Instantiation failure ({@link dandelion.database.Handler
	Handler}, {@link DynamicPageMaker DynamicPageMaker}). */

    public static final int CLASS_CREATE_FAILED		= 11;

//  public static final int SQL_CONNECT			= 12;

    /** SQL error. */
    public static final int SQL_OTHER			= 13;

//  public static final int SQL_PREPARE			= 14;

    /** I/O error while decaching. (Called <CODE>UPLOADER</CODE>
	probably for hysterical reasons.) */

    public static final int UPLOADER_IO			= 15;

    /** Internal error: malformed regular expression. */
    public static final int INTERNAL_REGEXP		= 16;

    /** Internal content error - most likely, some invariant about a
	regular expression match failed. */

    public static final int INTERNAL_CONTENT		= 17;

    /** Internal content error - some expected content, template or
	node is missing (cannot be found by its <CODE>KNOWNAS</CODE>
	name). */

    public static final int INTERNAL_KNOWNAS_MISSING	= 18;

    /** A search by <CODE>KNOWNAS</CODE> would deliver multiple
        results. */

    public static final int INTERNAL_KNOWNAS_DUPLICATE	= 19;

    /** Parameter problem (probably a failed conversion to integer
	in a servlet parameter). */

    public static final int FORMAT			= 20;

  private static String[] reasonDefinition = {
/* 0*/    "Undefined",
/* 1*/    "Bad request received",

/* 2*/    "Fetch content failure - SQL failed",
/* 3*/    "Store content failure - SQL failed",
/* 4*/    "Fetch content failure - Requested file not present",
/* 5*/    "Fetch content failure - IO failure",
/* 6*/    "Fetch content failure - content not present",

/* 7*/	  "Cache manager: I/O error",
/* 8*/	  "Cache manager: protocol error",
/* 9*/	  "Cache manager: protocol EOF",
				// EOF should always be caught internally.

/*10*/    "Internal error: class not found",
/*11*/    "Internal error: class failed to instantiate",

/*12*/    "SQL getConnection failure",
/*13*/    "SQL Exception",
/*14*/    "SQL Prepare statement failure",

/*15*/	  "Uploader I/O error",

/*16*/	  "Internal error (regular expressions)",
/*17*/	  "Internal error: database content not found",
/*18*/	  "Internal error: missing symbolic key",
/*19*/	  "Internal error: duplicate symbolic key",
/*20*/	  "Data format error"
  };

  private int itsReasonCode = UNDEFINED;
  private String itsReason;
  private Throwable itsTrace;

//  public FAILURE(String reason) {
//    this( reason, null );
//  }

  public FAILURE(String reason, Throwable trace) {
    itsReason = reason;
    itsTrace = trace;
  }

  public FAILURE(int reasonCode) {
    this( reasonCode, null, null );
  }

  public FAILURE(int reasonCode, String reason) {
    this( reasonCode, reason, null );
  }

  public FAILURE(int reason, Throwable trace) {
    this( reason, null, trace );
  }

  public FAILURE(int reasonCode, String reason, Throwable trace) {
    itsReasonCode = reasonCode;
    itsReason = reason;
    itsTrace = trace;
  }

    public int getReasonCode()
    {
	return itsReasonCode;
    }

  @Override
  public String toString() {
    StringWriter s = new StringWriter();
    PrintWriter p = new PrintWriter( s );

    if( itsReasonCode == -1 ) {
      p.println( "FAILURE [" + itsReason + "]" );
    } else {
      p.println( "FAILURE [" + itsReasonCode + " - " + reasonDefinition[ itsReasonCode ] + "]" );
      p.println( "SUPPLEMENTARY INFORMATION [" + itsReason + "]" );
    }
    if( itsTrace != null ) {
      p.println( "WRAPPED TRACE:" );
      itsTrace.printStackTrace( p );
    }

    return s.toString();
  }
}
