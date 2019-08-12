package com.hellohuandian.apps.testlibrary.core.observer;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-26
 * Description:
 */
public abstract class IgnoreErrorObserver<T> extends AtomicReference<Disposable>
        implements Observer<T>, Disposable
{
    private final boolean isCatch = true;

    protected abstract void on_Next(T t);

    @Override
    public final void onNext(T t)
    {
        if (!isDisposed())
        {
            if (isCatch)
            {
                try
                {
                    on_Next(t);
                }
                catch (Throwable e)
                {
                    onError(e);
                }
            } else
            {
                on_Next(t);
            }
        }
    }

    @Override
    public void onError(Throwable e)
    {
    }

    @Override
    public void onComplete()
    {
        if (!isDisposed())
        {
            lazySet(DisposableHelper.DISPOSED);
        }
    }

    @Override
    public final void dispose()
    {
        DisposableHelper.dispose(this);
    }

    @Override
    public final boolean isDisposed()
    {
        return get() == DisposableHelper.DISPOSED;
    }
}
