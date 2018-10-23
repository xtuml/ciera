/*----------------------------------------------------------------------------
 * File:  RSL_TU_bridge.c
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  template util (TU)
 * 
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#include "template_sys_types.h"
#include "RSL_TU_bridge.h"
#include "sys_xtumlload.h"
#include <stdlib.h>
#include <string.h>

/*
 * Bridge:  process_templates
 */
void
RSL_TU_process_templates( c_t * p_root_folder )
{
  c_t cmdline[256];

  // invoke the java parser
  const c_t * CMD = "java -cp \"$CLASSPATH\" io.ciera.template.rsl.parser.RSLParseUtil";
  memset( cmdline, 0, 256 );
  strcpy( cmdline, CMD );
  strcat( cmdline, " " );
  strcat( cmdline, p_root_folder );
  system( cmdline );

  // combine instances
  const c_t * CMD2 = "cat gen/code_generation/b.xtuml gen/code_generation/d.xtuml > gen/code_generation/e.xtuml";
  memset( cmdline, 0, 256 );
  strcpy( cmdline, CMD2 );
  system( cmdline );

  // load the newly created instances
  static char * a[2] = { "RSL_TU_process_templates", "gen/code_generation/e.xtuml" };
  Escher_xtUML_load( 2, a );
}
