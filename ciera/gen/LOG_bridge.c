/*----------------------------------------------------------------------------
 * File:  LOG_bridge.c
 *
 * Description:
 * Methods for bridging to an external entity.
 *
 * External Entity:  Logging (LOG)
 *
 *--------------------------------------------------------------------------*/

// This implementation is using the LogSuccess and LogFailure bridges to toggle
// tracing on and off. Only LogInfo should be used for actually tracing messages

#include "ciera_sys_types.h"
#include "LOG_bridge.h"

int LOG_EnableTracing = 0;

/*
 * Bridge:  LogSuccess
 */
void
LOG_LogSuccess( c_t * p_message )
{
    LOG_EnableTracing = 1;
}


/*
 * Bridge:  LogFailure
 */
void
LOG_LogFailure( c_t * p_message )
{
    LOG_EnableTracing = 0;
}


/*
 * Bridge:  LogInfo
 */
void
LOG_LogInfo( c_t * p_message )
{
  if ( LOG_EnableTracing) fprintf( stderr, "%s\n", p_message );
}

/*
 * Bridge:  LogTime
 */
void
LOG_LogTime( c_t * p_message, Escher_TimeStamp_t p_t )
{
  if ( LOG_EnableTracing ) fprintf( stderr, "%s %lu.%03lus\n", p_message, p_t / 1000, p_t % 1000 );
}
