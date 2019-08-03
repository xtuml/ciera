        ((${req_comp_name})components[${req_comp_index}]).${req_port_name}().satisfy(((${prov_comp_name})components[${prov_comp_index}]).${prov_port_name}());
        ((${prov_comp_name})components[${prov_comp_index}]).${prov_port_name}().satisfy(((${req_comp_name})components[${req_comp_index}]).${req_port_name}());
