/*
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.runtime.methods;

import com.oracle.truffle.api.*;
import com.oracle.truffle.api.frame.*;
import org.jruby.truffle.nodes.RubyRootNode;
import org.jruby.truffle.runtime.*;
import org.jruby.truffle.runtime.core.*;

public class CallTargetMethodImplementation implements MethodImplementation {

    private final CallTarget callTarget;
    private final MaterializedFrame declarationFrame;

    public CallTargetMethodImplementation(CallTarget callTarget, MaterializedFrame declarationFrame) {
        assert callTarget != null;

        this.callTarget = callTarget;
        this.declarationFrame = declarationFrame;
    }

    public Object call(PackedFrame caller, Object self, RubyProc block, Object... args) {
        assert RubyContext.shouldObjectBeVisible(self);
        assert RubyContext.shouldObjectsBeVisible(args);

        RubyArguments arguments = new RubyArguments(RubyArguments.create(declarationFrame, self, block, args));

        final Object result = callTarget.call(caller, arguments);

        assert RubyContext.shouldObjectBeVisible(result);

        return result;
    }

    public MaterializedFrame getDeclarationFrame() {
        return declarationFrame;
    }

    public void setMethod(RubyMethod method) {
        // Silently ignore if not a RootCallTarget for now - this is all going way in the next version of Truffle anyway

        if (callTarget instanceof RootCallTarget) {
            ((RubyRootNode) ((RootCallTarget) callTarget).getRootNode()).setMethod(method);
        }
    }

}
