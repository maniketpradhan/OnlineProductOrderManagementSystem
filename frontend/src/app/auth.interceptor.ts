import { HttpInterceptorFn } from "@angular/common/http";

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const saved = localStorage.getItem("oms-auth");
  const token = saved ? JSON.parse(saved).token : null;

  if (!token) {
    return next(request);
  }

  return next(
    request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    }),
  );
};
