getDomain("${req_comp_name}", ${req_comp_name}.class).${req_port_name}().setPeer(getDomain("${prov_comp_name}", ${prov_comp_name}.class).${prov_port_name}());
getDomain("${prov_comp_name}", ${prov_comp_name}.class).${prov_port_name}().setPeer(getDomain("${req_comp_name}", ${req_comp_name}.class).${req_port_name}());
