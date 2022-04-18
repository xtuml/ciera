getDomain("${req_comp_name}").getPort("${req_port_name}").setPeer(getDomain("${prov_comp_name}").getPort("${prov_port_name}"));
getDomain("${prov_comp_name}").getPort("${prov_port_name}").setPeer(getDomain("${req_comp_name}").getPort("${req_port_name}"));
