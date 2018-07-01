package io.ciera.template.rsl.translate.impl;

import io.ciera.cairn.util.Utility;
import io.ciera.summit.components.IComponent;
import io.ciera.template.RSL;
import io.ciera.template.rsl.parser.RSLParseUtil;
import io.ciera.template.rsl.translate.TU;

public class TUImpl<C extends IComponent<C>> extends Utility<C> implements TU {
    public TUImpl( C context ) {
        super( context );
    }
    public void process_templates( final String p_root_folder ) {
        RSLParseUtil.parse(p_root_folder, (RSL)context());
    }

}
