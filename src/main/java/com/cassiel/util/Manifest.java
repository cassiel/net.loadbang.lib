/**	[RCS	$RCSfile$
		@version $Revision$
		$Date$
	 RCS]
 */

package com.cassiel.util;

/** Manifest constants. */

public interface Manifest {
    /** "<CODE>true</CODE>" used in parameter files. */
    String TRUE = "true";

    /** "<CODE>true</CODE>" used in parameter files. */    
    String FALSE = "false";
    
    /** Property file stem for debugging logger settings. */
    String DEBUG_PROPERTYFILE = "debug";

    /** Dandelion page background. */
    String DANDELIONBG_colour = "#9999CC";

    /** Background colour for void cells in {@link
        dandelion.html.HTMLTable HTMLTable}. */
    String VOIDCELL_colour = "#808080";

    /** Colour for grey text (used for {@link
        dandelion.html.Widgets#hint hints}). */
    String GREYTEXT_colour = "#808080";

    /** Default cell background colour for {@link
        dandelion.html.HTMLTable HTMLTable}. */
    String DEFAULTCELL_colour = "WHITE";
}
