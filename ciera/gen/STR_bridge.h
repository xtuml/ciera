/*----------------------------------------------------------------------------
 * File:  STR_bridge.h
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  string (STR)
 * 
 * your copyright statement can go here (from te_copyright.body)
 *--------------------------------------------------------------------------*/

#ifndef STR_BRIDGE_H
#define STR_BRIDGE_H
#ifdef	__cplusplus
extern	"C"	{
#endif

#include "ciera_sys_types.h"
c_t * STR_itoa( const i_t );
i_t STR_atoi( c_t * );
c_t * STR_substr( const i_t, const i_t, c_t * );
i_t STR_strlen( c_t * );
i_t STR_indexof( c_t *, c_t * );
c_t * STR_getword( const i_t, const i_t, c_t * );
c_t * STR_trim( c_t * );
#define STR_quote() "\""
#define STR_openblockcomment() "/*"
#define STR_closeblockcomment() "*/"
c_t * STR_escapetics( c_t * );
c_t * STR_unescapetics( c_t * );
i_t STR_compare( c_t *, c_t * );

#ifdef	__cplusplus
}
#endif
#endif   /* STR_BRIDGE_H */
