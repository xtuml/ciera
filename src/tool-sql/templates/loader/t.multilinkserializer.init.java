        ${set_type_name} links = $l{referring_class}_inst.${selector_name}();
        for ( ${referred_name} ${iterator}_inst : links ) {
            out.print( "${link_type} ${rnum_str} IDS(" ); \
