/*
 * © 2024 SafeGuard
 * All rights reserved.
 *
 * Author: Mahesh
 *
 * Custom exception for handling common application errors.
 */

package org.ecommerce.exception;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorMessage;

    public ApplicationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
