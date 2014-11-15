
/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

// If there is no chat bar do nothing
if (!Y.one('#limsmuc-container .lims-bar')) {
    return;
}
// There is another instance of lims
else if (Y.one('#lims-container')) {
    return;
}
