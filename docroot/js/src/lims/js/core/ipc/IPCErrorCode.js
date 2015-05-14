/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * IPC Error codes
 */
Y.namespace('LIMS.Core');

/**
 * Error codes
 */
Y.LIMS.Core.IPCErrorCode = {
    // Input doesn't match
    wrongInput: 4000,
    // Input was too big (e.g. to many users)
    inputToBig: 4001,
    // Input doesn't match logical assumption (e.g. cannot create conversation with yourself)
    wrongAssumption: 4002,
    // Forbidden
    forbidden: 4003,
    // Not found
    notFound: 4004,
    // Server returned error
    serverError: 5000,
    // IPC Not enabled
    notEnabled: 5001
};
